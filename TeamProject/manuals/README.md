# LineUp

> Based on Spring Boot and yarn  
> Guide: <https://spring.io/guides/gs/spring-boot/>  
> Repository link: <https://github.com/NeverBehave/Lineup> (Private for now)

## Quick Start
### Requirement
- Build with JDK 15.0.2
	
	```
	java 15.0.2 2021-01-19
	Java(TM) SE Runtime Environment (build 15.0.2+7-27)
	Java HotSpot(TM) 64-Bit Server VM (build 15.0.2+7-27, mixed mode, sharing)
	```
- Maven 3.8.1 `Apache Maven 3.8.1 (05c21c65bdfed0f71a2f2ada8b84da59348c4c5d)`
- Spring-Boot
- Yarn 1.22.10

### Launch
Please launch the backend first and then frontend, you may need two terminal windows opened.

#### Backend

```Bash
cd $PROJECT_DIR
sh ./scripts/run_backend.sh
```

#### Frontend

```Bash
cd $PROJECT_DIR
sh ./scripts/run_frontend.sh
```

### Generate Javdoc
```
cd $PROJECT_DIR
bash ./scripts/generate_javadoc.sh
```

## Manual Startup 
### Frontend
##### Yarn install tutorial
- Windows: download from <https://classic.yarnpkg.com/en/docs/install/#windows-stable>
- macOS: `brew install yarn`
- Linux (Debian): `apt install yarn`

##### Build
- Install all dependencies for the project: `yarn install`
- Compiles and hot-reloads for development: `yarn run serve`
- Optional:
	- Compiles and minifies for production:`yarn run build`
	- Run your tests: `yarn run test`
	- Lints and fixes files: `yarn run lint`
- Customize configuration
	- Refer to [Configuration Reference](https://cli.vuejs.org/config/)

###Backend
- Setup in terminal

	```Bash
	cd $PROJECT_DIR
	./mvnw spring-boot:run
	```
- Setup in IDE
	- Main class: `com.example.demo.DemoApplication`

## Database Usage

In-memory, h2 based. Switch to file storage after finished

### Login Tutorial
- Step 1: URL
	- `localhost:8080/h2`
- Step 2: Login Setup
	- Saved Settings: `Generic H2 (Embedded)`
	- Driver Class: `org.h2.Driver`
	- JDBC URL `jdbc:h2:mem:lineup`
	- User Name: `sa`
	- Password: `password`

## Test Case

- Import `$PROJECT_DIR/manuals/hoppscotch-collection.json`
- Assume `localhost:8080`
- Testing in <https://hoppscotch.io/>

## Document Structure
- `$PROJECT_DIR/docs` stores the generated Javadoc files
- `$PROJECT_DIR/manuals` contains readme.txt, manual.pdf, tests.pdf, and any accompanying files
- `$PROJECT_DIR/scripts` contains scripts used for compiling *.java files and generate Javadoc files
- `$PROJECT_DIR/target` contains the generated web archive files
- `$PROJECT_DIR/src` contains all backend source files
- `$PROJECT_DIR/frontend` contains all frontend source files
	- `$PROJECT_DIR/frontend/docs` contains more detailed docs about frontend

### Frontend strcture
```Bash
$ tree -L 1 ./frontend
./frontend
├── README.md
├── babel.config.js
├── docs
├── jsconfig.json
├── package.json
├── postcss.config.js
├── public
├── src
├── vue.config.js
└── yarn.lock
```

### Backend structure
```Bash
$ tree ./main
./main
├── java
│   └── com
│       └── example
│           └── demo
│               ├── DemoApplication.java
│               ├── WebSecurityConfig.java
│               ├── event
│               │   ├── CustomEventRepo.java
│               │   ├── CustomEventRepoImpl.java
│               │   ├── Event.java
│               │   ├── EventController.java
│               │   ├── EventForm.java
│               │   ├── EventRepo.java
│               │   ├── EventUser.java
│               │   ├── EventUserPK.java
│               │   └── EventUserRepo.java
│               ├── log
│               │   ├── Log.java
│               │   └── LogRepo.java
│               ├── model
│               │   ├── BaseEntity.java
│               │   └── NamedEntity.java
│               ├── queue
│               │   ├── CustomQueueUserRepo.java
│               │   ├── Queue.java
│               │   ├── QueueController.java
│               │   ├── QueueRepo.java
│               │   ├── QueueUser.java
│               │   ├── QueueUserController.java
│               │   └── QueueUserRepo.java
│               ├── system
│               │   └── WelcomeController.java
│               └── user
│                   ├── CustomUserDetails.java
│                   ├── CustomUserDetailsService.java
│                   ├── CustomUserRepo.java
│                   ├── CustomUserRepoImpl.java
│                   ├── User.java
│                   ├── UserController.java
│                   ├── UserForm.java
│                   └── UserRepo.java
└── resources
    ├── application.properties
    └── templates
        ├── error.html
        ├── event
        │   ├── create.html
        │   └── index.html
        ├── fragments
        │   ├── inputField.html
        │   ├── layout.html
        │   └── selectField.html
        ├── login.html
        ├── logout.html
        ├── register.html
        ├── user
        │   ├── dashboard.html
        │   └── profile.html
        └── welcome.html
```

