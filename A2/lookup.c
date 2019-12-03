/*
 * Bryan Jay
 * 260738764
 *
 */

#define MAXRECORDS 100
#define MAXNAMELENGTH 15
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//Define structure to hold student data
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

//implicitly define all functions
void binarySearch(struct StudentRecord[], int, char[MAXNAMELENGTH]);
void quicksort(int, int, struct StudentRecord[]);

int main(int argc, char * argv[]) {

	struct StudentRecord SRecords[MAXRECORDS];
    int numrecords, nummarks, recordnum;


	//Read in Names and ID data
	FILE * NamesIDsDataFile;
	if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
		printf("Can't read from file %s\n", argv[1]);
		exit(1);
	}

	numrecords=0;
    	while (fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords[numrecords].FirstNames[0]),
		      				&(SRecords[numrecords].LastNames[0]),
		      				&(SRecords[numrecords].IDNums)) != EOF) {
	  numrecords++;
 	}

	fclose(NamesIDsDataFile);

	//Read in marks data
	FILE * MarksDataFile;
	if((MarksDataFile = fopen(argv[2], "r")) == NULL){
		printf("Can't read from file %s\n", argv[2]);
		exit(1);
	}
	nummarks=0;
	while(fscanf(MarksDataFile,"%d",&(SRecords[nummarks].Marks)) != EOF) {
	    nummarks++;
	}

	fclose(MarksDataFile);

	// Read in name that will be searched
	char name[MAXNAMELENGTH];
	sscanf(argv[3], "%s", &name);

	// sort the list of names
	quicksort(0, numrecords-1, SRecords);

	printf("The following record was found:");
	// search for name in sorted list
	binarySearch(SRecords, numrecords, name);

	return 0;
}


//Print student record
void printStudentRecords(struct StudentRecord SRecords[], int result){
	printf("\nName: %s %s \n", SRecords[result].FirstNames, SRecords[result].LastNames);
	printf("Student ID: %d \n", SRecords[result].IDNums);
	printf("Student Grade: %d \n", SRecords[result].Marks);
}


void swap(struct StudentRecord* record1, struct StudentRecord* record2){

	//Use pointers to swap values from address
	struct StudentRecord temp = *record2;
	*record2 = *record1;
	*record1 = temp;
}

void quicksort(int beginning, int end, struct StudentRecord SRecords[]){

	if(beginning < end){
		struct StudentRecord pivot;
		int middle = beginning;

		//establish pivot in front
		swap(&SRecords[(beginning + end)/2], &SRecords[beginning]);
		pivot = SRecords[beginning];

		//Separate both sides
		int i;
		for(i = beginning + 1; i <= end; i++){
			int retResult = strcmp(SRecords[i].LastNames, pivot.LastNames);

			if(retResult < 0){
				swap(&SRecords[++middle], &SRecords[i]);
			}

		}
		//Swap beginning record with the middle to order
		swap(&SRecords[beginning], &SRecords[middle]);

		// Recall the quicksort method to sort each side
		quicksort(beginning, middle-1, SRecords);
		quicksort(middle+1, end, SRecords);
	}
}

void binarySearch(struct StudentRecord SRecords[], int length, char name[MAXNAMELENGTH]){
	int left = 0;
	int right = length - 1;
	int center = (left + right)/2;

	//Continue searching until its gone through the whole list
	while(left <= right){
		//Compare records last name with middle result
		int retResult = strcmp(SRecords[(left+right)/2].LastNames, name);

		//if equal print record
		if(retResult == 0){
			printStudentRecords(SRecords, center);
			break;

		} else if(retResult < 0){
			left = center + 1;

		} else {
			right = center - 1;
		}
		center = (left + right)/2;
	}

	//if no record found return below
	if(left > right){
		printf("No record found for student with last name %s", name);
	}
}
