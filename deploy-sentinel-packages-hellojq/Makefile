all:    publish

clean:
	rm -fr quickstart-build.tar.gz

build:	clean
	mkdir -p packages/
	tar -czf quickstart-build.tar.gz packages
	mkdir -p /tmp/packages/
	cp hellojq.deb /tmp/packages/

publish: build quickstart.py
	python quickstart.py
        



