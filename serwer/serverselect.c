#include <sys/types.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <sys/param.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>

#define SERVER_PORT 1234
#define QUEUE_SIZE 5

struct user{
  char nick[20];
  char pass[20]
};

int main(int argc, char* argv[])
{
   int nSocket, nClientSocket;
   int nBind, nListen;
   int nFoo = 1, nTmp, nMaxfd, nFound, nFd;
   struct sockaddr_in stAddr, stClientAddr;
   static struct timeval tTimeout;
   fd_set fsMask, fsRmask, fsWmask;
   char buf[]="Zuzanna Sawala\n";
   char wiadomosc[5];
   char tekst[30];
  //  char nick[5];
  //  char pass[5];
  //  char test[20];
    char length[2];
   /* address structure */
   memset(&stAddr, 0, sizeof(struct sockaddr));
   stAddr.sin_family = AF_INET;
   stAddr.sin_addr.s_addr = INADDR_ANY;
   stAddr.sin_port = htons(SERVER_PORT);

   /* create a socket */
   nSocket = socket(AF_INET, SOCK_STREAM, 0);
   if (nSocket < 0)
   {
       fprintf(stderr, "%s: Can't create a socket.\n", argv[0]);
       exit(1);
   }
   setsockopt(nSocket, SOL_SOCKET, SO_REUSEADDR, (char*)&nFoo, sizeof(nFoo));

   /* bind a name to a socket */
   nBind = bind(nSocket, (struct sockaddr*)&stAddr, sizeof(struct sockaddr));
   if (nBind < 0)
   {
       fprintf(stderr, "%s: Can't bind a name to a socket.\n", argv[0]);
       exit(1);
   }

   /* specify queue size */
   nListen = listen(nSocket, QUEUE_SIZE);
   if (nListen < 0)
   {
       fprintf(stderr, "%s: Can't set queue size.\n", argv[0]);
   }

   FD_ZERO(&fsMask);
   FD_ZERO(&fsRmask);
   nMaxfd = nSocket;
   nTmp = sizeof(struct sockaddr);

   while(1)
   {
       /* block for connection request -- timeout after 5 sec. */
       FD_SET(nSocket, &fsRmask);
       fsWmask = fsMask;
       tTimeout.tv_sec = 5;
       tTimeout.tv_usec = 0;
       printf("select...\n");
       nFound = select(nMaxfd + 1, &fsRmask, &fsWmask, (fd_set*) 0, &tTimeout);

       if (nFound < 0)
       {
               fprintf(stderr, "%s: Select error.\n", argv[0]);
       }
       if (nFound == 0)
       {
               printf("%s: Timed out.\n", argv[0]);
               fflush(stdout);
       }
       if (FD_ISSET(nSocket, &fsRmask))
       {
                nClientSocket = accept(nSocket, (struct sockaddr*)&stClientAddr, &nTmp);
                if (nClientSocket < 0)
                {
                        fprintf(stderr, "%s: Can't create a connection's socket.\n", argv[0]);
                        exit(1);
                }
                /* connection */
                printf("%s: [connection from %s]\n", argv[0],
                       inet_ntoa((struct in_addr)stClientAddr.sin_addr));
                FD_SET(nClientSocket, &fsMask);
    printf("wysyla\n" );
		read(nClientSocket,wiadomosc,sizeof(wiadomosc));
    printf("%s\n",wiadomosc,5 );
     read(nClientSocket,tekst,sizeof(tekst)); //jedno zczytanie całej wiadomosci
    // printf("%s\n",tekst );
    //podział wiadomosci na interesujace mnie czesci
    if(!strncmp("login",wiadomosc),5){
      int c=0;
      char nick[10];
      char pass[10];
        int i=0;
       while(c<2){
      printf("jest\n" );
      int w=0;
        while(tekst[i]!='\n'){
          if(c==0){
      nick[w]=tekst[i];
    }
    if(c==1){
      pass[w]=tekst[i];
    }
  i=i+1;
  w=w+1;
    }
    w=0;
    i=i+1;
    c=c+1;
  }
    printf("%s\n",nick );
    printf("%s\n", pass );
     }

                if (nClientSocket > nMaxfd) nMaxfd = nClientSocket;
       }
       for (nFd = 0; nFd <= nMaxfd; nFd++)
       {
                if (FD_ISSET(nFd, &fsWmask))
                {
                        printf("%s: [sending string to %s]\n", argv[0],
                               inet_ntoa((struct in_addr)stClientAddr.sin_addr));
		if(nFd==nClientSocket){
		// 	if(!strncmp("117217",wiadomosc,6)){
		// 	write(nFd,"Zuzanna Sawala",15);
		// }
		}
                        FD_CLR(nFd, &fsMask);
                        close(nFd);
                }
       }
   }

   close(nSocket);
   return(0);
}
