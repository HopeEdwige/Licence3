let l = [1; 2; 3; 4; 5; 6];;


let rec contains (l, e) = match l with
    [] -> false
  | x::r -> if x = e then true else contains(r, e);;

assert(contains(l, 3));;
assert(not(contains(l, 42)));;



let add (l, e) = if contains(l, e) then l else e::l;;

assert(add(l, 7) = [7; 1; 2; 3; 4; 5; 6]);;
assert(add(l, 3) = l);;



let rec union (l, m) = match m with
    [] -> l
  | x::r -> if contains(l, x) then union(l, r) else x::union(l, r);;

assert(union(l, [7; 8; 9]) = [7; 8; 9; 1; 2; 3; 4; 5; 6]);;
assert(union(l, [5; 6; 7; 8]) = [7; 8; 1; 2; 3; 4; 5; 6]);;
assert(union(l, [1; 2; 3]) = l);;



let rec intersection (l, m) = match m with
    [] -> []
  | x::r -> if contains(l, x) then x::intersection(l, r) else intersection(l, r);;

assert(intersection(l, [7; 8; 9]) = []);;
assert(intersection(l, [5; 6; 7; 8]) = [5; 6]);;
assert(intersection(l, [1; 2; 3]) = [1; 2; 3]);;



let rec difference (l, m) = match m with
    [] -> l
  | x::r -> if contains(l, x) then difference(l, r);;

assert(difference(l, [7; 8; 9]) = l);;
assert(difference(l, [5; 6; 7; 8]) = [1; 2; 3; 4]);;
assert(difference(l, [1; 2; 3]) = [4; 5; 6]);;
