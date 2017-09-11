all:    publish

clean:
	rm -fr quickstart-build.tar.gz

build:	clean
	tar -czf quickstart-build.tar.gz helloworld.deb

publish: build quickstart.py
	python quickstart.py
        



