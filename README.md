yukaku
- ├─doc                     プロジェクトドキュメント、補足
- ├─xq-base                共通処理
- ├─xq-service             サービス層
- │  ├─service-base        共通サービス処理
- │  └─service-sys         業務サービス
- ├─xq-web                 コントローラー、画面処理
- │  ├─config              コンフィグ処理
- │  ├─exception           コントローラー共通異常処理
- │  └─XqApplication.java アプリケーション起動用
- ├─resources
- │  ├─static           タイムリーフテンプレートファイル
- │  ├─application.yml        コンフィグ
- │  ├─application-dev.yml    コンフィグ　－　開発環境
- │  └─application-test.yml   コンフィグ　－　テスト環境
- │  └─application-pro.yml    コンフィグ　－　本番環境

開発環境
- MyBatis       http://www.mybatis.org/mybatis-3/zh/index.html
- Druid        https://github.com/alibaba/druid
- Thymeleaf     http://www.thymeleaf.org/
- Slf4j        https://www.slf4j.org/
- Swagger2      http://swagger.io/
- Maven        http://maven.apache.org/
- JDK1.8       http://www.oracle.com/technetwork/java/index.html

- Redis        https://redis.io/　   
- RabbitMQ      https://www.rabbitmq.com/  
- JStorm        http://jstorm.io/
- Jenkins       https://jenkins.io/index.html


前提：redis install , rabbitmq, 
- MYSQL導入 /yukaku/sql/yukaku.sql
- xq-web起動　XqApplication.java　http://localhost:8082/swagger-ui.html
- xq-front-demo起動 　FrontApplication.java　admin/adminで登録できる


