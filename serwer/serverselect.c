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
#define SERVER_IP 192.168.0.12

int main(int argc, char* argv[])
{
   int nSocket, nClientSocket;
   int nBind, nListen;
   int nFoo = 1, nTmp, nMaxfd, nFound, nFd;
   struct sockaddr_in stAddr, stClientAddr;
   static struct timeval tTimeout;
   fd_set fsMask, fsRmask, fsWmask;
   char buf[]="Zuzanna Sawala\n";
   char wiadomosc[6];
   /* address structure */
   memset(&stAddr, 0, sizeof(struct sockaddr));
   stAddr.sin_family = AF_INET;
   stAddr.sin_addr.s_addr = htonl(SERVER_IP);
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
		read(nClientSocket,wiadomosc,6);
                if (nClientSocket > nMaxfd) nMaxfd = nClientSocket;
       }
       for (nFd = 0; nFd <= nMaxfd; nFd++)
       {
                if (FD_ISSET(nFd, &fsWmask))
                {
                        printf("%s: [sending string to %s]\n", argv[0],
                               inet_ntoa((struct in_addr)stClientAddr.sin_addr));
		if(nFd==nClientSocket){
			if(!strncmp("117217",wiadomosc,6)){
			write(nFd,"Zuzanna Sawala",15);
		}
		}
                        FD_CLR(nFd, &fsMask);
                        close(nFd);
                }
       }
   }

   close(nSocket);
   return(0);
}
