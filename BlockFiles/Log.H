#ifndef __LOG_H
#define __LOG_H

typedef struct _MatrixData MatrixData;
typedef struct _LogVar LogVar;
typedef struct _LogInfo LogInfo;


struct _MatrixData
{
    char name[128];			/* name of variable to be logged */
    int col;				/* data column number */
    unsigned long row;		/* data row number */
    unsigned long index;	/* data index */
    double *element;		/* pointer to */
};


struct _LogVar
{
    MatrixData Data;
    LogVar *next;
};


struct _LogInfo
{
    LogVar *logVarsList;
};

#endif
