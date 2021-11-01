# Watchdog 

## 安装thrift0.13.0

```
brew tap-new $USER/local-tap
brew extract --version='0.13.0' thrift $USER/local-tap
brew install thrift@0.13.0
```

```shell
thrift -gen java -out common/src/main/java/  common/src/main/thrift/*.thrift
```