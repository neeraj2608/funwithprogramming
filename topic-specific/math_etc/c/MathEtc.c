#include <assert.h>
/*
 * Find the square of a number without using * or ^ operators
 * */
int square(x){
    if(x == 1) return 1;

    int y = x/2;
    int z = square(y);
    if(x%2 == 0)
        // x is even
        // x = 2y
        // z = y^2 = (x/2)^2 = (x^2)/4
        // => x^2 = z*4
        return z << 2;
    else
        // x is odd
        // y = (x-1)/2 (since y is an int) => x = 2y + 1
        // Now, x^2 - y^2 = (2y+1)^2 - y^2
        //                = 4y^2 + 4y + 1 - y^2
        //            x^2 = 4y^2 + 4y + 1
        //                = 4z + 4y + 1
        return z << 2 + y << 2 + 1;

}

int main(){
    assert(square(1)==1);
    assert(square(2)==4);
    assert(square(7)==49);
    assert(square(8)==64);
}
