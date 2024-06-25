package com.ezdev.cat_randomizer.downloader

interface Downloader {
    fun downloadFile(url: String, title: String = "image.jpg"): Long
}