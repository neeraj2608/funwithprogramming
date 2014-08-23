#include <stdio.h> //for printf

// Reverse position of words in a string in-place.
// Words are separated by spaces and may include punctuation as part
// of the word.
// Example: "Hello world, it's a beautiful day!" ->
//          "day! beautiful a it's world, Hello"
void reverseWordsInPlace(char* str){
    reverseWords_(str,0,strlen(str)-1);
    int index = 0;
    int start = 0;
    while(str[index]!='\0'){
        if(str[index]==' '){
            reverseWords_(str,start,index-1);
            while(str[index]==' ') index++; //skip additional spaces
            start = index;
        }
        else index++;
    }
}

void reverseWords_(char* str, int start, int finish){
    while(1){
        if(start >= finish) break; //mistake start == finish won't work for even-length strings!

        char temp = str[start];
        str[start] = str[finish];
        str[finish] = temp;
        start++;
        finish--;
    }
}

// remove whitespace in-place from a string
// O(n)
void removeWhitespaceInPlace(char* str){
    int index = 0;
    int count = 0;
    while(str[index] != '\0'){
        while(str[index] == ' ') index++;
        str[count++] = str[index++];
    }
    str[count] = '\0';
}

int main(){
    // you MUST use an array here! char* str = "hello there" won't work
    // because strings constants are read-only
    char strreverse[] = "Hello world, it's a beautiful day!";
    reverseWordsInPlace(strreverse);
    printf("%s\n",strreverse);

    char strwhitespace[] = " hello there";
    removeWhitespaceInPlace(strwhitespace);
    printf("%s\n",strwhitespace);
}

