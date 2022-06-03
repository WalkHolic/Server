# WalkHolic Server
# 산책로 및 공원 추천 지도 안드로이드 어플리케이션
###
-----------------------
## Description
산책로 코스를 생성하고 다른 사용자들과 공유할 수 있는 WalkHolic은 산책로 및 공원 관련 정보를 제공하는 지도 어플입니다. 낯선 곳에서의 산책 코스를 어플을 통해 확인하고 여행이나 데이트 코스를 미리 계획해보세요.

맛집이나 여행지 추천하는 지도 어플리케이션은 많은 사용자들이 리뷰와 사진을 통해 정보를 활용하고 있습니다. 그러나 산책로 코스는 현지인이나 블로그를 통해 확인하는 경우가 많습니다. 따라서 가고 싶은 곳 주변의 산책로 코스를 WalkHolic을 통해 손쉽게 확인하고 계획을 짜보세요.
![image](https://user-images.githubusercontent.com/68843443/171858187-0b3b4d14-9f87-4554-9991-58b73967200c.png)


## Environment
### Android (Client)
- Android Studio 7.1.2
- Java JDK 11
- Gradle 7.1.2
- Galaxy S21
- Pixel 2 API 30
### Spring (Server)
- Spring Boot 2.6.5
- IntelliJ
- Java JDK 11
- Gradle 7.4.1
### AWS EC2
- Amazon Linux 2 Kernel 5.10 AMI
- Architecture: X86_64
- Instance type: t2.micro
- vCPU 1
- RAM 1GB
### AWS RDS
- MariaDB 10.6.7
- db.t3.micro
- vCPU 2
- RAM 1GB
- Storage 20GB

## Clone Project
```shell
git clone https://github.com/WalkHolic/Server.git
```

## REST API
### API Reference
- URI: https://walkhoic.shop - This is WalkHolic API Base url. You can use your own localhost server link instead this Link. (http://localhost:8080)
### Park API
| Park API                     | URL                                        | HTTP Method |
| ---------------------------- | ------------------------------------------ | ----------- |
| Park List                    | /park/{id}                                 | GET         |
| Park List within 5km         | /park/nearParks?lat={lat}&lng={lng}        | GET         |
| Filtering Option Park List   | /park/filter?lat={lat}&lng={lng}           | POST        |
| Create Park Review           | /park/{id}/review                          | POST        |
| Retrieve Park Review         | /park/{id}/review                          | GET         |
| Update Park Review           | /park/review/{id}                          | DELETE      |
| Delete Park Review           | /park/review/{id}                          | PUT         |


