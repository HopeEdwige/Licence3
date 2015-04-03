tp56: tp56.ml
	ocamlbuild -cflags -dtypes -pp camlp4o tp56.byte

run: tp56.byte
	./tp56.byte

clean:
	rm -rf ./_build
	rm tp56.byte