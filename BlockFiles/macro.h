#ifndef __MACRO_H
#define __MACRO_H

#include <math.h>

#define PI 3.141592653589793
#define round(a) ((a)>0 ? (int)((a)+0.5) : -(int)(0.5-(a)))
#define IsSampleHitTime(multiple) ((simcount%(multiple)==0) ? 1 : 0)
#define Interpol(x,x1,x2,y1,y2) (y1+((y2-y1)/(x2-x1))*(x-x1))

//double scale;
int IsSampleHitTime2(double t, double Ts)
{
	double offset = 0.0;
	int d = round((t-offset)/Ts);
	double dd = (t-offset)/Ts;

	if(fabs(d-dd) < 0.1*(double)STEPSIZE/Ts)
		return 1;
	else
		return 0;

}


int GetLookupIndex(double* look, int look_size, double u)
{
	int xlen, idx, bottom, top;
	double *x;

	idx = 0;
	xlen = look_size;
	bottom = 0;
	top = xlen-1;
	x = look;

	/*
	* Deal with the extreme cases first:
	*   if u <= x[bottom] then return idx = bottom
	*   if u >= x[top]    then return idx = top-1
	*/
	if (u <= x[bottom]) {
		return(bottom);
	}
	else if (u >= x[top]) {
		return(top-1);
	}

	if (u < 0)
	{
	   /* For negative input find index such that: x[idx] <= u < x[idx+1] */
		for (;;) {
			idx = (bottom + top)/2;
			if (u < x[idx]) {
				top = idx - 1;
			}
			else if (u >= x[idx+1]) {
				bottom = idx + 1;
			}
			else {
			   /* we have x[idx] <= u < x[idx+1], return idx */
				return(idx);
			}
		}
	}
	else
	{
	   /* For non-negative input find index such that: x[idx] < u <= x[idx+1] */
		for (;;) {
			idx = (bottom + top)/2;
			if (u <= x[idx]) {
				top = idx - 1;
			}
			else if (u > x[idx+1]) {
				bottom = idx + 1;
			}
			else
			{
			   /* we have x[idx] < u <= x[idx+1], return idx */
				return(idx);
			}
		}
	}
}


double GausiRand(double m, double v)
{
	double v1,v2,rsq,fac,gset;

	do
	{
		v1 = 2.0*( ((double)rand()) / (RAND_MAX + 1.0) ) - 1.0;
		v2 = 2.0*( ((double)rand()) / (RAND_MAX + 1.0) ) - 1.0;
		rsq = v1*v1 + v2*v2;
	} while(rsq >= 1.0 || rsq == 0.0);

	fac = sqrt((double)(-2.0*v*log(rsq))/rsq);
	gset = (double)(v1*fac);

	if(rand()%2 == 0)
		return m + v2*fac;
	else
		return m - gset;
}

#endif
