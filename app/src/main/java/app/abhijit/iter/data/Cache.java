/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Abhijit Parida <abhijitparida.me@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package app.abhijit.iter.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import app.abhijit.iter.models.Student;

/**
 * This class handles local storage and retrieval of Student objects.
 */
public class Cache {

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public Cache(Context context) {
        this.sharedPreferences = context.getSharedPreferences("students", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    @Nullable
    public Student getStudent(@Nullable String username) {
        try {
            return gson.fromJson(this.sharedPreferences.getString(username, null), Student.class);
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Map.Entry<String, ?> entry : this.sharedPreferences.getAll().entrySet()) {
            Student student = getStudent(entry.getKey());
            if (student != null) {
                students.add(student);
            }
        }

        return students;
    }

    public void setStudent(@Nullable String username, @Nullable Student student) {
        if (student == null) {
            this.sharedPreferences.edit().remove(username).apply();
        } else {
            this.sharedPreferences.edit().putString(username, gson.toJson(student)).apply();
        }
    }
}
