# 这是代码测试

![text](video.mp4)

::: tip 

sdf

:::

```c
#include <stdio.h>
int main()
{
  int i,j,k;
  printf("\n");
  for(i=1;i<5;i++) /*以下为三重循环*/
    for(j=1;j<5;j++)
      for (k=1;k<5;k++)
      {
        if (i!=k&&i!=j&&j!=k) /*确保i、j、k三位互不相同*/
        printf("%d,%d,%d\n",i,j,k);
      }
  return 0;
}
```



```c#
using System;
namespace HelloWorldApplication
{
    /* 类名为 HelloWorld */
    class HelloWorld
    {
        /* main函数 */
        static void Main(string[] args)
        {
            /* 我的第一个 C# 程序 */
            Console.WriteLine("Hello World!");
            Console.ReadKey();
        }
    }
}
```

```c++
//x64处理器 64位window10 vs2015 
#include <iostream>
using namespace std;
int main()
{
	bool b;
	char c;short s; int i; long l; long long ll; float f; double d; long double ld;long float lf;
	unsigned char uc; unsigned short us; unsigned int ui; unsigned long ul; unsigned long long ull;
	cout << sizeof(bool) <<  endl;
	cout << sizeof(char)<<" " << sizeof(short)<<" "<< sizeof(signed int) << " " << sizeof(long) << " " << sizeof(signed long long) << " " << sizeof(float) << " " << sizeof(double) << " " << sizeof(long float) << " " << sizeof(long double) << endl;
	cout <<sizeof(unsigned char)<<" "<< sizeof(unsigned short) << " " << sizeof(unsigned int) << " " << sizeof(unsigned long) << " " << sizeof(unsigned long long) << endl;
	cout << sizeof(unsigned) << endl;
	
	
	cout << "hello World!!!" <<endl;
	system("pause");
	return 0;
}
```

