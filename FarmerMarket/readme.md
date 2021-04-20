# BattleShip

## Environment

Code and scripts are tested under with following settings.

### Linux 

#### Distribution

NixOS https://nixos.org

```bash
$ uname -a
Linux home-thinkcenter 5.4.90 #1-NixOS SMP Sun Jan 17 13:05:38 UTC 2021 x86_64 GNU/Linux
```

#### JDK

```bash
$ javac --version
javac 15.0.1
$ java --version
openjdk 15.0.1 2020-10-20
OpenJDK Runtime Environment (build 15.0.1+0-adhoc..jdk15u-jdk-15.0.1-ga)
OpenJDK 64-Bit Server VM (build 15.0.1+0-adhoc..jdk15u-jdk-15.0.1-ga, mixed mode, sharing)
```

#### Nix

If you have `Nix` installed, you may use `nix-shell` and it will set up development environment for you. 

### Windows

Virtualbox (Oracle VM VirtualBox VM Runner v6.1.16) used.

https://modern.ie Image from https://developer.microsoft.com/en-us/microsoft-edge/tools/vms/

#### Distribution

![](./windows.png)

#### JDK

```powershell
PS C:\Users\IEUser> javac --version
javac 15.0.2
PS C:\Users\IEUser> java --version
java 15.0.2 2021-01-19
Java(TM) SE Runtime Environment (build 15.0.2+7-27)
Java HotSpot(TM) 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)
```

### Database

```
+--------------------------+-------------------------------+
| Variable_name            | Value                         |
+--------------------------+-------------------------------+
| admin_tls_version        | TLSv1,TLSv1.1,TLSv1.2,TLSv1.3 |
| immediate_server_version | 999999                        |
| innodb_version           | 8.0.23                        |
| original_server_version  | 999999                        |
| protocol_version         | 10                            |
| slave_type_conversions   |                               |
| tls_version              | TLSv1,TLSv1.1,TLSv1.2,TLSv1.3 |
| version                  | 8.0.23                        |
| version_comment          | MySQL Community Server - GPL  |
| version_compile_machine  | x86_64                        |
| version_compile_os       | Linux                         |
| version_compile_zlib     | 1.2.11                        |
+--------------------------+-------------------------------+
```

### Tomcat

### Maven

```bash
$ mvn --version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /nix/store/0nxykym6mgrhh4brbhcnhsg8rhywzgd9-apache-maven-3.6.3/maven
Java version: 15.0.1, vendor: N/A, runtime: /nix/store/56m3nlv1nfw7r3bwbwd1xz9vhbydym7q-openjdk-15.0.1-ga/lib/openjdk
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.97", arch: "amd64", family: "unix"
```

#### Note for javadoc

By default, installation from Java won't add javadoc to path. Please add Java bin path to `Control Panel -> System and Security -> System -> Advance System Setting -> Environment Variables -> Path` with your install path so the script can work properly.

![](./windows_path.png)

## Usage

The following command are happened under project root directory

Please setup the environment follow the `manual/install.txt` first before running the program!

## Others

### `manuals/`

Program usages, and test explanations

#### `compile-pdf.sh`

Compile `.md` file into `pdf`. `pandoc` used
