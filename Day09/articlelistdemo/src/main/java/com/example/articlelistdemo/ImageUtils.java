package com.example.articlelistdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/*
    图片工具类
 */
public class ImageUtils {
    //  一级缓存
    private static Map<String, Bitmap> memoryCaches = new HashMap<>();

    //  二级缓存根目录
    private static File cacheFileDir;

    //  下载图片的线程池
    private static ExecutorService downloadService = Executors.newCachedThreadPool();

    //  Handler，用于线程间通信
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /*
        使用三级缓存，加载网络图片，并设置到ImageView中
     */
    public static void load(Context context, String imageUrl, ImageView imageView) {
        //  将imageUrl与imageView关联起来
        imageView.setTag(imageUrl);

        //  1.首先从一级缓存中取
        Bitmap memoryBitmap = memoryCaches.get(imageUrl);
        if (memoryBitmap != null) {
            //  显示图片
            imageView.setImageBitmap(memoryBitmap);
            return;
        }

        //  初始化图片存储
        initCacheFileDir(context);

        //  2.一级缓存不存在，则从二级缓存中取
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        File cacheFile = new File(cacheFileDir, imageName);
        if (cacheFile.exists()) {
            String imagePath = cacheFile.getAbsolutePath();
            Bitmap cacheBitmap = BitmapFactory.decodeFile(imagePath);
            //  显示图片
            imageView.setImageBitmap(cacheBitmap);
            //  将图片加入到一级健存
            memoryCaches.put(imageUrl, cacheBitmap);
            return;
        }

        //  3.从服务器获取图片
        Runnable runnable = () -> {

            //  判断ImageView是否已经被复用了
            Boolean shouldDownload = false;
            Object tag = imageView.getTag();
            if (tag != null && tag instanceof String) {
                String newImageUrl = (String) tag;
                shouldDownload = newImageUrl.equals(imageUrl);
            } else {
                shouldDownload = false;
            }

            //  如果ImageView被复用了，就不要再下载图片了
            if (!shouldDownload) {
                return;
            }

            HttpURLConnection connection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                URL url = new URL(imageUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);
                connection.connect();
                is = connection.getInputStream();
                fos = new FileOutputStream(cacheFile);
                byte[] data = new byte[10];
                int length = -1;
                while ((length = is.read(data)) != -1) {
                    //  将图片写入到二级缓存
                    fos.write(data, 0, length);
                }

                //  切换至主线程
                mHandler.post(() -> {

                    //  判断ImageView是否已经被复用了
                    Boolean shouldLoad = true;
                    Object object = imageView.getTag();
                    if (object != null && tag instanceof String) {
                        String newImageUrl = (String) object;
                        shouldLoad = newImageUrl.equals(imageUrl);
                    } else {
                        shouldLoad = false;
                    }

                    //  如果ImageView已经被复用了，则不设置Bitmap
                    if (!shouldLoad) {
                        return;
                    }

                    String imagePath = cacheFile.getAbsolutePath();
                    Bitmap cacheBitmap = BitmapFactory.decodeFile(imagePath);
                    //  显示图片
                    imageView.setImageBitmap(cacheBitmap);
                    //  将图片加入到一级健存
                    memoryCaches.put(imageUrl, cacheBitmap);
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        };

        downloadService.execute(runnable);
    }

    /*
        初始化二级缓存
     */
    private static void initCacheFileDir(Context context) {
        if (cacheFileDir == null) {
            File rootDir = null;
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                //  使用外部存储进行图片缓存
                rootDir = Environment.getExternalStoragePublicDirectory(null);
            } else {
                //  使用内部存储作为图片缓存
                rootDir = context.getFilesDir();
            }
            cacheFileDir = new File(rootDir, "image_caches");
            if (!cacheFileDir.exists()) {
                cacheFileDir.mkdirs();
            }
        }
    }
}
