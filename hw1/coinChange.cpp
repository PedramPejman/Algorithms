#include <iostream>
#include <string>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <locale.h>

using namespace std;
int coins [4]  = {25, 10, 5, 1};
char coin_names [4] = {'Q', 'D', 'N', 'P'};

void print_coin (int index, int freq) {
  for (int i=0; i<freq; i++) {
    cout << coin_names[index] << " " ;
  }
}
void process (float amount) {
  if (amount < 0) {
    if (amount == -1) return;
    cout << "Amount cannot be negative" << endl;
    return;
  }
  int index = 0;
  setlocale(LC_NUMERIC, "");
  printf("$%'.2f ",amount);
  amount -= floor(amount);
  amount = round (amount * 100);
  while (index < 4) {
    print_coin(index, amount / coins[index]);
    amount -= floor(amount / coins[index]) * coins[index];
    index++;
  }
  cout << endl;
}
int main(int argc, char** argv) {
  if (argc < 2) exit(1);
  FILE *file = fopen(argv[1], "r");
  float amount = 0;
  while ((!feof(file))&&(amount != -1.00)) {
    fscanf(file, "%f", &amount);
    process(amount);
  }
}

