package com.zy.service;

import java.io.File;
import java.util.List;

public interface WordCloudService {
    public List<String> readTxt(String fileName);
    public String getImageStr(String imgFile);
    public void deleteFile(File file);
    public String wordCloud(List<String> words);
}
