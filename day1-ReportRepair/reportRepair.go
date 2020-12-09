package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {
	file, err := os.Open("./numbers.txt")
	if err != nil {
		log.Fatal(err)
	}

	defer file.Close()

	numbers := make([]int, 0)

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		convertedNum, _ := strconv.Atoi(scanner.Text())
		numbers = append(numbers, convertedNum)
	}

	complementMap := make(map[int]int)

	for number := range numbers {
		complementMap[numbers[number]] = 2020 - numbers[number]
	}

	for _, value := range complementMap {
		keyValue, exists := complementMap[value]

		if exists == true {
			fmt.Println(keyValue * value)
			break
		}
	}

}
