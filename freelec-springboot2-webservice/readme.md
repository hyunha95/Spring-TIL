키페어 pem파일을 ~/.ssh/로 복사한다. ~/.ssh/ 디렉토리로 pem파일을 옮겨 놓으면 ssh 실행 시 pem키 파일을 자동으로 읽어 접속을 진행한다. 이후부터는 별도로 pem키 위치를 명령어로 지정할 필요가 없게 된다.
```
cp pem키를 내려받은 위치 ~/.ssh/
```
pem키가 잘 복사되었는지 ~/.ssh 디렉토리로 이동해서 파일 목록을 확인해 본다.
```
cd ~/.ssh/
ll
```
복사되었다면 pem키의 권한을 변경한다.
```
chmod 600 ~/.ssh/pem키이름
```
권한을 변경하였다면 pem키가 있는 ~/.ssh 디렉토리에 config 파일을 생성한다.
```
vim ~/.ssh/config
```
다음과 같이 본인이 원하는 Host로 등록한다. Host를 앞으로 접속할 키값으로 보면 된다. 예를 들어 Host abc로 등록하면 ssh abc로 해당 EC2로 접속할 수 있게 된다. Host 외에 HostName은 탄력적 IP주소를 사용하면 된다.
```
Host freelec-springboot2-webservice
  HostName
  User ec2-user
  IdentityFile ~/.ssh/freelec-springboot2-webservice.pem
```
