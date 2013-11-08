/*********************************************************************
 * File Name : ode_rk4.c
 *
 * Abstract : ODE Solver - Runge-Kutta 4th order method
 *
 *
 *********************************************************************/
#include <stdio.h>
#include <string.h>

extern void EvalOutput( );
extern void StateDerivative();

extern int numofcstate;
extern double step, t;
extern double CState[];
extern double *dxdt;
extern double x0[], k0[], k1[], k2[], k3[];
extern int IsMajorTimeStep;

void UpdateContinuousState()
{
    int i;
    double ts, tmp;

    ts = t;
    (void)memcpy(x0,CState, numofcstate*sizeof(double));

	IsMajorTimeStep = 0;

    dxdt = k0;
    StateDerivative();

    tmp = 0.5*step;
    for(i=0;i<numofcstate;i++){
        CState[i] = x0[i] + tmp*k0[i];
    }
    // 여기까지 주요 라인
    t = ts + tmp;
	dxdt = k1;
    EvalOutput();
    StateDerivative();

    for(i=0;i<numofcstate;i++){
        CState[i] = x0[i] + tmp*k1[i];
    }
	dxdt = k2;
    EvalOutput();
    StateDerivative();
    for(i=0;i<numofcstate;i++){
        CState[i] = x0[i] + step*k2[i];
    }

    t = ts + step;
	dxdt = k3;
    EvalOutput();
    StateDerivative();
    tmp = step/6.0;
    for(i=0;i<numofcstate;i++){
        CState[i] = x0[i] + tmp*(k0[i] + 2.0*k1[i] + 2.0*k2[i] + k3[i]);
    }

	IsMajorTimeStep = 1;
}
