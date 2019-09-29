package com.free.time.sensitive.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * 配置工具类-单例<br>
 * 
 * @author hailin0@yeah.com
 * @createDate 2016年3月11日
 * @update 2017年5月16日 by changle
 * 更新内容:
 * 1.增强敏感词文件读取方式,可手动传入文件地址
 * 2.兼容敏感词配置文件中文乱码问题
 * 3. url方式传入敏感词文件.
 * 
 */
@Slf4j
public final class Config {

    /**
     * 配置文件名
     */
    private String DEFAULT_CONF_FILE_NAME = "sensitive-word.properties";

    // 缓存配置数据
    private Map<String, String> cacheConfig = new HashMap<String, String>();

    /**
     * 当前部署环境根元素
     */
    private String root;

    /**
     * 实例-volatile
     */
    private static volatile Config conf;

    private Config(String file, FileTypeEnum fileTypeEnum) {
        InputStream in = null;
        try {
            if (fileTypeEnum == FileTypeEnum.HTTP){
                //从http读取

                URL url =new URL(file); // 创建URL
                URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码
                urlconn.connect();
                HttpURLConnection httpconn =(HttpURLConnection)urlconn;
                int httpStatus = httpconn.getResponseCode();
                if(httpStatus == HttpURLConnection.HTTP_OK) {
                    in = urlconn.getInputStream();
                } else {
                   log.warn("获取词库文件失败! fileUrl={}", file);
                }
            }else{
                in = getClass().getClassLoader().getResourceAsStream(StringUtils.isBlank(file)?DEFAULT_CONF_FILE_NAME:file);
            }
            init(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    private Config(String fileName) {
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(StringUtils.isBlank(fileName)?DEFAULT_CONF_FILE_NAME:fileName);
            init(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init(InputStream in) throws IOException {
        Properties prop = new Properties();
        prop.load(in);

        // 一次性装载
        Set<Object> keySet = prop.keySet();
        Object value;
        for (Object key : keySet) {
            value = prop.get(key);
            cacheConfig.put(String.valueOf(key), String.valueOf(value));
            // System.out.println(String.valueOf(key) + "=" + String.valueOf(value));
        }
        // root根元素配置
        if (null != cacheConfig.get("root") && !"".equals(cacheConfig.get("root"))) {
            root = cacheConfig.get("root");
        }
    }

    /**
     * 获取唯一实例
     * 
     * @return
     */
    public static Config newInstance() {
        if (null == conf) {
            synchronized (Config.class) {
                if (null == conf) {
                    conf = new Config(null);
                }
            }
        }
        return conf;
    }

    public static Config newInstance(String filename) {
        if (null == conf) {
            synchronized (Config.class) {
                if (null == conf) {
                    conf = new Config(filename);
                }
            }
        }
        return conf;
    }

    public static Config newInstance(String fileUrl, FileTypeEnum fileTypeEnum) {
        if (null == conf) {
            synchronized (Config.class) {
                if (null == conf) {
                    conf = new Config(fileUrl, fileTypeEnum);
                }
            }
        }
        return conf;
    }

    /**
     * 获取全部配置数据
     * 
     * @return
     */
    public Map<String, String> getAll() {
        return cacheConfig;
    }

    /**
     * 基于root根元素的配置获取.<br>
     * key格式：<br>
     * 如配置为:develop.img_upload_server_path 则key为：img_upload_server_path<br>
     * 如配置为:img_upload_server_path 则key为：img_upload_server_path<br>
     * 默认以root.key获取.获取不到则直接根据key获取<br>
     * 如果未配置root元素，则直接以key获取<br>
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
        String value = null;
        // root为前缀获取
        if (null != root) {
            value = cacheConfig.get(root + "." + key);
        }
        // 无前缀.直接key获取
        if (null == value || "".equals(value)) {
            value = cacheConfig.get(key);
        }
        if (null == value) {
            throw new RuntimeException("config key is not found !");
        }
        return value;
    }

    /**
     * 获取int
     * 
     * @param key
     * @return
     */
    public int getInt(String key) {
        String propertie = getString(key);
        if (null == propertie) {
            return 0;
        }
        return Integer.valueOf(propertie);
    }

    /**
     * 获取Long
     * 
     * @param key
     * @return
     */
    public long getLong(String key) {
        String propertie = getString(key);
        if (null == propertie) {
            return 0;
        }
        return Long.valueOf(propertie);
    }

    /**
     * 获取Boolean
     * 
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        String propertie = getString(key);
        if (null == propertie) {
            return false;
        }
        return Boolean.valueOf(propertie);
    }

    /**
     * 当前是运行在生产环境
     * 
     * @return
     */
    public boolean isRuningProduc() {
        boolean b = null == root || "".equals(root) ? false : root.endsWith("produc");
        return b;
    }

    public enum FileTypeEnum{
        LOCAL,HTTP
    }
}
