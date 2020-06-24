//cria 5 threads e mosta ID da thread


#include <unistd.h>
#include <sys/types.h>
#include <stdio.h>
#include <pthread.h>
#define MAX_THREADS 5

int contador_global = 0;

void *exercicio03(void *arg)
{
	pthread_t tid =	pthread_self();
 	printf("Nova thread criada. TID = %ld\n", tid);
 	
 	contador_global = contador_global + tid;
 	printf("contador_global + tid = %d",contador_global);
 	
 	pthread_exit(NULL);
}

int main ()
{
 	pthread_t threads[MAX_THREADS];
 	
 	for(int i=0; i<MAX_THREADS; i++)

 		pthread_create(&threads[i], NULL, exercicio02, NULL);	//exercicio02 eh a rotina da thread
 		printf("main thread aguarda a finalização de todas as worker threads antes de terminar... \n");

	return 0;
}


-----------------------

#include <stdio.h>
#include <pthread.h>
#define MAX_THREADS 5

void *exercicio02(void *arg)
{
	pthread_t tid =	pthread_self();
 	printf("Nova thread criada. TID = %ld\n", tid);
 	pthread_exit(NULL);
}

int main ()
{
 	pthread_t threads[MAX_THREADS];
 	
 	for(int i=0; i<MAX_THREADS; i++)

 		pthread_create(&threads[i], NULL, exercicio02, NULL);	//exercicio02 eh a rotina da thread
 		printf("main thread aguarda a finalização de todas as worker threads antes de terminar... \n");

	return 0;
}


-----------------------
void *exercicio01(void *arg) 
{
	pthread_t tid =	pthread_self();
 	printf("Nova thread criada. TID = %ld!\n", tid);
 	pthread_exit(NULL);
}

int main ()
{
 	pthread_t thread;
 	int i;

 	printf("A criar uma nova thread\n");
 	i = pthread_create(&thread, NULL, exercicio01, NULL);
 	pthread_exit(NULL);
}
