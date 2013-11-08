/*
 *	File Name : daga_log.c
 *
 *	Abstract : Routines for data logging
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>  
#include "Log.h"

/*
 * External Variables 
 */

extern int numofoutblock;
extern char sys_name[];
extern double start, end, step;
extern double scale; //jonghwa Dec.19

LogInfo *logInfo;  /* Variables for Data Logging */

/*
 * Function Definition
 */

void WriteData(LogVar *, FILE *);


/*
 * Function Name : CreateSystemLogVar
 *
 */
void CreateSystemLogVar()
{
    logInfo = (LogInfo *)malloc(sizeof(LogInfo));
    logInfo->logVarsList = NULL;
}


/*
 * Function Name : CreateDataLogVar 
 *
 */
LogVar *CreateDataLogVar(char *var_name, int data_length)
{
	LogVar *var, *varList;
	unsigned long total_count;

	total_count = (unsigned long)ceil((end-start)/step +1.);  /* math.h is used here */
	var = (LogVar *)malloc(sizeof(LogVar));
	var->next = NULL;
	strcpy(var->Data.name, var_name);
	var->Data.col = data_length;
	var->Data.row = total_count;
	var->Data.index = 0;
	var->Data.element = (double *)malloc(data_length*total_count*sizeof(double));
	varList = logInfo->logVarsList;
	if(varList !=NULL){
		while(varList->next !=NULL){
			varList = varList->next;
		}
		varList->next = var;
	}
	else{
		logInfo->logVarsList = var;
	}
	return(var);
}


/*
 * Function Name : DataLog
 *
 */
void DataLog(LogVar *var, double *Input, int data_length)
{
	double *data;
	unsigned long index;

	data = var->Data.element;
	index = var->Data.index;
	memcpy(&data[index], Input, data_length*sizeof(double));
	var->Data.index+=data_length;
}


/*
 * Function Name : DestroyLogVar
 *
 */
void DestroyLogVar()
{
	LogVar *head;
	head = logInfo->logVarsList; 
	while(head){
		LogVar *var = head;
		head = var->next;
		free(var->Data.element);
		free(var);
	}

	free(logInfo);
}



/*
 * Function Name : StopLog
 *
 */
void StopLog()
{

/* 
 * If you want to save the data in CemTool format, i.e. **.var,
 * use the following by changing #if 0 to #if 1
 */
#if 0
	FILE *fptr;
	LogVar *var;
	char var_name[132];
	char no_name[12]=" ";

	strcpy(var_name, sys_name);
	strcat(var_name,".var");

	if ((fptr = fopen(var_name,"w")) == NULL){
		(void)fprintf(stderr, "*** Error opening %s\n", var_name);
		exit(0);
	}
	
	fprintf(fptr, "VariableList\n");  // **.var 파일의 제일 상단부를 적는다. 
	
	fprintf(fptr, "%d\n", numofoutblock+1); // variable의 수 

	var = logInfo->logVarsList;
	
	while (var !=NULL){
		WriteData(var, fptr);
		var = var->next;
	}
	
	fclose(fptr);
#endif


/* 
 * If you want to save the data in Matlab format, i.e. **.mat,
 * use the following by changing #if 0 to #if 1
 */
#if 1
	FILE *fptr;
	LogVar *var;
	char var_name[132];
	char no_name[12]=" ";

	strcpy(var_name, sys_name);
	strcat(var_name,".mat");

	if ((fptr = fopen(var_name,"wb")) == NULL){
		(void)fprintf(stderr, "*** Error opening %s\n", var_name);
		exit(0);
	}
	
	var = logInfo->logVarsList;
	
	while (var !=NULL){
		WriteData(var, fptr);
		var = var->next;
	}
	
	fclose(fptr);
#endif 

}



/*
 * Function Name : WriteData
 *
 */
void WriteData(LogVar *var, FILE *fptr)
{
/* 
 * If you want to save the data in CemTool format, i.e. **.var,
 *  use the following by changing #if 0 to #if 1
 */
#if 0
	double *data;
	int col, j;
	unsigned long row, i;
	char var_name[128];

	strcpy(var_name, var->Data.name);
	col = var->Data.col;
	row = var->Data.row;
	data = var->Data.element;

	fprintf(fptr, "%s\n", var_name); // Variable Name
	fprintf(fptr, "1\n");
	fprintf(fptr, "%d %d\n", row, col);
	for (i=0;i<row;i++){
		for(j=0;j<col;j++){
			fprintf(fptr, "%.14f\t", data[i*col+j]);
		}
		fprintf(fptr, "\n");
	}
	fprintf(fptr, "``\n");

	return;
#endif


/* 
 * If you want to save the data in Matlab format, i.e. **.mat,
 * use the following by changing #if 0 to #if 1
 */
#if 1
	int i,j;
	long row, col, namelen;
	long type = 0;
	long imagf = 0;
	double *data;
	char var_name[128];

	strcpy(var_name, var->Data.name);
	namelen = (long)strlen(var_name)+1;
	col = (long)var->Data.col;
	row = (long)var->Data.row;
	data = var->Data.element;

	fwrite(&type, sizeof(long), 1, fptr);
	fwrite(&row, sizeof(long), 1, fptr);
	fwrite(&col, sizeof(long), 1, fptr);
	fwrite(&imagf, sizeof(long), 1, fptr);
	fwrite(&namelen, sizeof(long), 1, fptr);
	fwrite(var_name, sizeof(char), namelen, fptr);

	for(i=0;i<col;i++){
		for(j=0;j<row;j++){
			fwrite(&data[j*col+i], sizeof(double), 1, fptr);
		}
	}
#endif

}




