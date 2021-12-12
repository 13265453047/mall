#!/bin/bash
# 把已经编译好的opencv_lib.tar放入hgbserver目录下面解压，在/etc/ld.so.conf.d/目录下面建立一个opencv4.conf，插入/usr/local/services/hgbserver/lib
# 安装部分依赖 yum install gtk2-devel，就可以使用opencv了

# 安装依赖
yum -y install epel-release
yum install gcc gcc-c++
yum install cmake3
yum install gtk2-devel
yum install ant
yum install python-devel numpy
yum install ffmpeg-devel
yum install -y unzip zip

# 下载opencv
unzip opencv-3.4.7.zip
cd  opencv-3.4.7
mkdir build
cd build/

# 编译安装
#cmake -D WITH_TBB=ON -D WITH_EIGEN=ON ..
#cmake -D CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local ..
#make
#make install

# 编译安装java版本
yum install ant
cmake3 -D CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local -DBUILD_TESTS=OFF ..
make -j8
make install

# so库连接配置
cd /etc/ld.so.conf.d
sudo sh -c 'echo "/usr/local/lib64" > opencv4.conf'
sudo ldconfig

# 查看lib目录下面的so