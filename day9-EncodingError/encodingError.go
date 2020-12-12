package main

import (
	"bufio"
	"fmt"
	"log"
	"math/rand"
	"os"
	"strconv"
)

func abs(x int) int {
	if x < 0 {
		return -x
	}
	return x
}

func quicksort(a []int) []int {
	if len(a) < 2 {
		return a
	}

	left, right := 0, len(a)-1

	pivot := rand.Int() % len(a)

	a[pivot], a[right] = a[right], a[pivot]

	for i := range a {
		if a[i] < a[right] {
			a[left], a[i] = a[i], a[left]
			left++
		}
	}

	a[left], a[right] = a[right], a[left]

	quicksort(a[:left])
	quicksort(a[left+1:])

	return a
}

func main() {

	//Read input from file and store numbers in an array
	file, err := os.Open("./input.txt")
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

	preambleLength := 25

	currentPos := preambleLength
	conditionMet := true

	//PART 1
	invalidNum := 0
	for currentPos < len(numbers) {
		if !conditionMet && currentPos > 0 {
			fmt.Println(numbers[currentPos-1])
		}

		previousNumbers := make([]int, 0)

		for x := currentPos - preambleLength; x < currentPos; x++ {
			previousNumbers = append(previousNumbers, numbers[x])
		}

		satisfied := false

	myLoop:
		for i := 0; i < len(previousNumbers); i++ {
			for j := 1; j < len(previousNumbers); j++ {
				if previousNumbers[i] != previousNumbers[j] && previousNumbers[i]+previousNumbers[j] == numbers[currentPos] {
					satisfied = true
					break myLoop
				}
			}
		}

		if !satisfied {
			invalidNum = numbers[currentPos]
			break
		}

		currentPos++
	}

	//PART 2
	windowSum := 0

	contiguousArr := make([]int, 0)

primary:
	for i := 0; i < len(numbers); i++ {
		windowSum = 0
		for j := i; j < len(numbers); j++ {
			if windowSum+numbers[j] < invalidNum {
				contiguousArr = append(contiguousArr, numbers[j])
				windowSum += numbers[j]
			} else if windowSum+numbers[j] > invalidNum {
				contiguousArr = contiguousArr[:0]
				break
			} else {
				contiguousArr = append(contiguousArr, numbers[j])
				break primary
			}
		}
	}

	sortedContiguousArr := quicksort(contiguousArr)
	smallest := sortedContiguousArr[0]
	largest := sortedContiguousArr[len(sortedContiguousArr)-1]

	fmt.Println("DAY 2 Answer:", smallest+largest)

}
