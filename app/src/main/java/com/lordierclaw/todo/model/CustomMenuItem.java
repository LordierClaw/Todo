package com.lordierclaw.todo.model;

import android.graphics.drawable.Drawable;

import com.lordierclaw.todo.listener.IMenuListener;

import java.util.Objects;

public class CustomMenuItem {
    private Drawable icon;
    private String title;
    private IMenuListener listener;
    private final boolean isDivider;

    public CustomMenuItem() {
        isDivider = true;
    }

    public CustomMenuItem(Drawable icon, String title, IMenuListener listener) {
        this.icon = icon;
        this.title = title;
        this.listener = listener;
        isDivider = false;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IMenuListener getListener() {
        return listener;
    }

    public void setListener(IMenuListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomMenuItem that = (CustomMenuItem) o;
        return isDivider == that.isDivider && Objects.equals(icon, that.icon) && Objects.equals(title, that.title) && Objects.equals(listener, that.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, title, listener, isDivider);
    }

    public boolean isDivider() {
        return isDivider;
    }
}
