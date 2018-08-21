#include <stdio.h>
#include <stdlib.h>
#ifdef __APPLE__
#include <OpenCL/opencl.h>
#else
#include <CL/opencl.h>
#endif
#define ARRAY_LENGTH 1000

int main(int argc, char** argv)
{
	/* Variáveis para armazenamento de referências a
	objetos OpenCL */
	cl_platform_id platformId;
	cl_device_id deviceId;
	cl_context context;
	cl_command_queue queue;
	cl_program program;
	cl_kernel kernel;
	cl_mem bufA;
	cl_mem bufB;
	cl_mem bufC;

	/* Variáveis diversas da aplicação */
	int* hostA;
	int* hostB;
	int* hostC;
	size_t globalSize[1] = { ARRAY_LENGTH };
	int i;

	/* Código-fonte do kernel */
	const char* source =
	"__kernel void ArrayDiff( \
	__global const int* a, \
	__global const int* b, \
	__global int* c) \
	{ \
	int id = get_global_id(0); \
	c[id] = a[id] - b[id]; \
	}";

	/* Obtenção de identificadores de plataforma
	e dispositivo. Será solicitada uma GPU. */
	clGetPlatformIDs(1, &platformId, NULL);
	clGetDeviceIDs(platformId, CL_DEVICE_TYPE_GPU,
	1, &deviceId, NULL);
	/* Criação do contexto */
	context = clCreateContext(0, 1, &deviceId,
	NULL, NULL, NULL);
	/* Criação da fila de comandos para o
	dispositivo encontrado */
	queue = clCreateCommandQueue(context, deviceId,
	0, NULL);
	/* Criação do objeto de programa a partir do
	código-fonte armazenado na string source */
	program = clCreateProgramWithSource(context, 1, &source,
	NULL, NULL);
	/* Compilação do programa para todos os
	dispositivos do contexto */
	clBuildProgram(program, 0, NULL, NULL, NULL, NULL);
	/* Obtenção de um kernel a partir do
	programa compilado */
	kernel = clCreateKernel(program, "ArrayDiff", NULL);
	/* Alocação e inicialização dos arrays no hospedeiro */
	hostA = (int*) malloc(ARRAY_LENGTH * sizeof(int));
	hostB = (int*) malloc(ARRAY_LENGTH * sizeof(int));
	hostC = (int*) malloc(ARRAY_LENGTH * sizeof(int));
	for (i = 0; i < ARRAY_LENGTH; ++i)
	{
	hostA[i] = rand() % 101 - 50;
	hostB[i] = rand() % 101 - 50;
	}
	/* Criação dos objetos de memória para comunicação com
	a memória global do dispositivo encontrado */
	bufA = clCreateBuffer(context, CL_MEM_READ_ONLY,
	ARRAY_LENGTH * sizeof(int), NULL, NULL);
	bufB = clCreateBuffer(context, CL_MEM_READ_ONLY,
	ARRAY_LENGTH * sizeof(int), NULL, NULL);
	bufC = clCreateBuffer(context, CL_MEM_READ_WRITE,
	ARRAY_LENGTH * sizeof(int), NULL, NULL);
	/* Transferência dos arrays de entrada para a memória
	do dispositivo */
	clEnqueueWriteBuffer(queue, bufA, CL_TRUE, 0,
	ARRAY_LENGTH * sizeof(int), hostA, 0,
	NULL, NULL);
	clEnqueueWriteBuffer(queue, bufB, CL_TRUE, 0,
	ARRAY_LENGTH * sizeof(int), hostB, 0,
	NULL, NULL);
	/* Configuração dos argumentos do kernel */
	clSetKernelArg(kernel, 0, sizeof(cl_mem), &bufA);
	clSetKernelArg(kernel, 1, sizeof(cl_mem), &bufB);
	clSetKernelArg(kernel, 2, sizeof(cl_mem), &bufC);
	/* Envio do kernel para execução no dispositivo */
	clEnqueueNDRangeKernel(queue, kernel, 1, NULL,
	globalSize, NULL, 0, NULL, NULL);
	/* Sincronização (bloqueia hospedeiro até término da
	execução do kernel */
	clFinish(queue);
	/* Transferência dos resultados da computação para a
	memória do hospedeiro */
	clEnqueueReadBuffer(queue, bufC, CL_TRUE, 0,
	ARRAY_LENGTH * sizeof(int), hostC, 0,
	NULL, NULL);
	/* Impressão dos resultados na saída padrão */
	for (i = 0; i < ARRAY_LENGTH; ++i)
	printf("%d - %d = %d\n", hostA[i], hostB[i], hostC[i]);
	/* Liberação de recursos e encerramento da aplicação */
	clReleaseMemObject(bufA);
	clReleaseMemObject(bufB);
	clReleaseMemObject(bufC);
	clReleaseKernel(kernel);
	clReleaseProgram(program);
	clReleaseCommandQueue(queue);
	clReleaseContext(context);
	free(hostA);
	free(hostB);
	free(hostC);
	return 0;
}
