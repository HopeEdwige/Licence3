asteroids: asteroids.ml
	ocamlc -thread graphics.cma unix.cma asteroids.ml -o asteroids

run:
	ocaml graphics.cma unix.cma asteroids

test:
	ocamlrun asteroids

clean:
	rm asteroids asteroids.cmi asteroids.cmo
