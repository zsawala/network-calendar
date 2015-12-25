#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
int main(int argc, char* argv[]){
	//pierwszy arg-- lab-net-2.cs.put.poznan.pl -- domena mojego kompa
	//drugi argument -- port
	// if(argc!=4){
	// 	printf("za malo argsow\n");
	// 	return 0;
	// }
	printf("blabla\n" );
	printf("%s",argv[1]);
	struct hostent* addrent;
	int fd=socket(PF_INET,SOCK_STREAM,0);
	struct sockaddr_in sa;
	// w kliencie socket to jest socket servera tak w praktyce
	
	sa.sin_family=PF_INET;
	sa.sin_port=htons(1234);
	// addrent=gethostbyname(argv[1]);
	// memcpy(&sa.sin_addr.s_addr,addrent->h_addr,addrent->h_length);
	sa.sin_addr.s_addr=inet_addr("192.168.0.12"); // tutaj musi byc adress ip kompa na którym uruchomiony jest server
	printf("polaczylo\n");
	char buf[300];
	connect(fd,(struct sockaddr*)&sa,sizeof(sa));
	printf("%s%d",argv[1],sizeof(argv[1]));
	write(fd,argv[1],sizeof(argv[1]));
	int n=read(fd,buf,sizeof(buf));
	buf[n]='\0';
	write(1,buf,n);
	close(fd);

	return 0;

}
