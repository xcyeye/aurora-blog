package xyz.xcye.test;

import cn.hutool.core.codec.Morse;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import xyz.xcye.util.EncryptUtil;
import xyz.xcye.util.FileUtil;

import java.io.*;
import java.net.URL;
import java.security.KeyPair;
import java.util.List;

/**
 * @author qsyyke
 */


public class Main {
    public static void main(String[] args) throws Exception {
        FileUtil.writeByStream(FileUtil.getInputStream("/Users/aurora/Documents/aurora主题文件/aurora-study.mp4"),
                "D:/Users/aurora/Documents/aurora主题文件/bb/aa//aurora-back.mp4");
    }
}
