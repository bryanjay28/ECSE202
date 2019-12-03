/*
 * Bryan Jay
 * 260738764
 *
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXRECORDS 100
#define MAXNAMELENGTH 15

//Initialize the student record structure
struct StudentRecord
{
	char FirstNames[MAXNAMELENGTH];
	char LastNames[MAXNAMELENGTH];
	int IDNums;
	int Marks;
};

//initialize the binary tree nodes, with recalling structs within
struct treeNode {
	struct StudentRecord SRecords;
	struct treeNode* left;
	struct treeNode* right;
};

//Implicitly define all the prototypes
void binarySearch(struct StudentRecord**, int, char[MAXNAMELENGTH]);
void printStudentRecords(struct StudentRecord**, int);
struct treeNode* insertNode(struct treeNode* root, struct StudentRecord* SRecords);
int traverseTree(struct StudentRecord**, struct treeNode*);

int main(int argc, char * argv[]) {
	// Initialize struct to store the records and the treeNode root to sort in Binary tree
	struct StudentRecord SRecords;
	struct treeNode* root = NULL;


	//Read in Names and ID data
		FILE * NamesIDsDataFile;
		if((NamesIDsDataFile = fopen(argv[1], "r")) == NULL){
			printf("Can't read from file %s\n", argv[1]);
			exit(1);
		}
		FILE * MarksDataFile;
		if((MarksDataFile = fopen(argv[2], "r")) == NULL){
			printf("Can't read from file %s\n", argv[2]);
			exit(1);
		}
		int numrecords = 0;

		// Read in files data of both Marks and NamesIDs at the same time store in binary tree
		while ((fscanf(NamesIDsDataFile,"%s%s%d",&(SRecords.FirstNames[0]),
				      				&(SRecords.LastNames[0]),
				      				&(SRecords.IDNums)) != EOF) && fscanf(MarksDataFile,"%d",&(SRecords.Marks)) != EOF) {

			  numrecords++;
			  // Creating binary tree nodes
			  root = insertNode(root,&SRecords);
		 	}

		fclose(NamesIDsDataFile);
		fclose(MarksDataFile);

		char inputName[MAXNAMELENGTH];

		// Get  inputed name to search
		if( argc == 4 ){
			sscanf(argv[3],"%s",inputName);
		} else {
			printf("Please input Name");
		}

		//Initialize pointer array to store pointers to structures
		struct StudentRecord** SRecordArray = malloc(numrecords*sizeof(struct StudentRecord **));

		//Store the binary tree into an array
		traverseTree(SRecordArray, root);

		//Search for name
		binarySearch(SRecordArray, MAXRECORDS, inputName);
}

struct treeNode* insertNode(struct treeNode* root,struct StudentRecord* SRecords){

	//If given root is empty create a node by allocating memory and giving a value
	if (root == NULL) {

		root = (struct treeNode*) malloc(sizeof(struct treeNode));
		root->SRecords = *SRecords;
		root->left = NULL;
		root->right = NULL;

		return root;
	}
	//Compare if string is greater than or less than string at root then either store it on the left or the right of the node if empty
	else if(strcasecmp(root->SRecords.LastNames, SRecords->LastNames) > 0) {
		root->left = insertNode(root->left,SRecords);
	}
	else {
		root->right = insertNode(root->right,SRecords);
	}

	return root;
}

//Traverse the tree from left, root, right order and store the value into the array
int traverseTree(struct StudentRecord** Srecords, struct treeNode* root){
	static int index = 0;
	if (root->left != NULL) {
		index = traverseTree(Srecords,root->left);
	}

	Srecords[index] = &(root->SRecords);
	index++;

	if(root->right != NULL) {
		index =traverseTree(Srecords,root->right);
	}

	return index;
}

//Print the found student record
void printStudentRecords(struct StudentRecord **SRecords, int result){
	printf("The following record was found:");
	printf("\nName: %s %s \n", SRecords[result]->FirstNames, SRecords[result]->LastNames);
	printf("Student ID: %d \n", SRecords[result]->IDNums);
	printf("Student Grade: %d \n", SRecords[result]->Marks);
}

void binarySearch(struct StudentRecord** SRecords, int length, char name[MAXNAMELENGTH]){
	int left = 0;
	int right = length - 1;
	int center = (left + right)/2;

	//Continue searching until its gone through the whole list
	while(left <= right){
		//Compare records last name with middle result
		int retResult = strcasecmp(SRecords[(left+right)/2]->LastNames, name);

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
