import sys
x = int(sys.argv[1])
def numbers(x):
    x += 1
    return x
if __name__ == "__main__":
    print numbers(x)
    