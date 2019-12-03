/*
 * Bryan Jay
 * 260738764
 *
 */
#include <stdio.h>

void decimalToBase(int number, int base);
void decimalToBaseTest(int number, int base);



int main(int argc, char *argv[] ) {

	int inputBase;
	int inputNumber;
	// check if the correct number of values were inputed
	if(argc == 3){
		sscanf(argv[2], "%d", &inputBase);
		sscanf(argv[1], "%d", &inputNumber);

	} else if(argc == 2){
	//if 1 argument inputed then assume base is 2
		sscanf(argv[1], "%d", &inputNumber);
		inputBase = 2;
	} else {
		printf("Please input 2 Arguments");
		return 0;
	}

	// Print answer and call function to print value
	printf("The Base-%d form of the %d is: ", inputBase, inputNumber);
	decimalToBase(inputNumber, inputBase);


	return 0;
}

void decimalToBaseTest(int number, int base){

	// initialize character array with all possible values
	char LUT[50] = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	int i = 0;
	int temp = 0;
	//initialize integer array to carry the remainder
	int remainder[500];

	while(number > 0){
		temp = number/base;
		//save the remainder from each division
		remainder[i] = number%base;
		number = temp;
		i++;
	}

	int j;
	//print function in reverse
	for(j=i-1; j >= 0; j--){

		printf("%c", LUT[remainder[j]]);

	}

}

void decimalToBase(int number, int base){
	int i = 0;
	int temp = 0;
	//initialize array for answer
	char remainder[500];

	while(number > 0){
		// divide number to find remainder
		temp = number/base;
		// the remainder is the number saved for the answer
		remainder[i] = number%base;
		number = temp;
		i++;
	}

	int j;
	//print answer in reverse
	if(base > 9){
		for(j=i-1; j >= 0; j--){
			if(remainder[j] > 9){
				remainder[j] += 55;
				printf("%c", remainder[j]);
			} else {
				printf("%d", remainder[j]);
			}
		}
	} else {
		for(j=i-1; j >= 0; j--){
			printf("%d", remainder[j]);
		}
	}

}

