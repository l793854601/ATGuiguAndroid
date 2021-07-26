package com.example.aidlservice;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Student implements Parcelable {
    private int id;
    private String name;
    private double price;

    public static List<Student> students() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Tom1", 101));
        students.add(new Student(2, "Tom2", 102));
        students.add(new Student(3, "Tom3", 103));
        return students;
    }

    public Student() {
    }

    public Student(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(price);
    }
}
