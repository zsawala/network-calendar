#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <signal.h>
#include <stdio.h>
//linkowanie gcc -Wall -lpthread
struct cln {
	int cfd;
	struct sockaddr_in caddr;
};
void* cthread(void* arg){
	struct cln* c=(struct cln*) arg;
	printf("new connection: %s\n",inet_ntoa((struct in_addr)c->caddr.sin_addr));
	char buf[]="Zuzanna Sawala\n";
	char wiadomosc[300];
	read(c->cfd,wiadomosc,sizeof(wiadomosc));
	if(!strncmp("117217",wiadomosc,6))
		write(c->cfd,buf,sizeof(buf));
	else {char buf[]="BLAD - to nie autor\n"; write(c->cfd,buf,sizeof(buf));};
		close(c->cfd);
	close(c->cfd);
	free(c);
}
int main(){
	pthread_t tid;
	socklen_t slt;
	int fd=socket(PF_INET,SOCK_STREAM,0);
	int on=1;
	struct sockaddr_in sa;//server addr
// STRUKTURA SOCKETA
	sa.sin_family=PF_INET;
	sa.sin_port=htons(1234);
	sa.sin_addr.s_addr=INADDR_ANY; //przypisuje wewnetrzny adres kompa
	setsockopt(fd,SOL_SOCKET,SO_REUSEADDR,(char*)&on,sizeof(on));//zeby zwalnial port po zabiciu procesu
	bind(fd,(struct sockaddr*)&sa,sizeof(sa));
	listen(fd,15);
	while(1){
	struct cln* c=malloc(sizeof(struct cln));
	int wielkosc_sockaddr=sizeof(c->caddr);
	c->cfd=accept(fd,(struct sockaddr*)&c->caddr,&wielkosc_sockaddr);
	pthread_create(&tid,NULL,cthread,c);
	pthread_detach(tid);
	}
	close(fd);
	return 0;

}
