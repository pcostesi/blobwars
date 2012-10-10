package structures;

class Pair<F, S>{
	F first;
	S second;
	
	Pair(F first, S second){
		this.first = first;
		this.second = second;
	}
	
	F getFirst(){
		return first;
	}
	
	S getSecond(){
		return second;
	}
	
	void setFirst(F first){
		this.first = first;
	}
	
	void setSecond(S second){
		this.second = second;
	}
}
