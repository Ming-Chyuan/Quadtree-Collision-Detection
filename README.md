## Chrome Extension
[GitHub with MathJax](https://chrome.google.com/webstore/detail/github-with-mathjax/ioemnmodlmafdkllaclgeombjnmnbima) : 方便顯示數學公式  

## Project Environment
[Processing in Eclipse](https://processing.org/tutorials/eclipse/)

## Why use Quadtree?
若有 $n$ 個點，逐一對每個點求其與其他點的距離為  
```java
for(Ball b : balls) { // balls.size() == n
    for(Ball other : balls) {
        if(b != other && b.intersects(other)) {
            b.setHighlight(true);
        }
    }
}
```
上述方法為 $O(n^2)$ ，就算省略一些重複的計算如下  
```java
for(int i = 0; i < balls.length; i++) {
	for(int j = i + 1; j < balls.length; j++) {
        if(b != other && b.intersects(other)) {
            b.setHighlight(true);
        }
    }
}
```
結果依然是 $(n-1) + (n-2) + ... + 1 + 0 = \frac{n(n-1)}{2} = O(n^2)$  

而搜尋一次 [Quadtree](https://en.wikipedia.org/wiki/Quadtree) ( $T(n) = T(\frac{n}{4}) + C_1$ ) 與 BST 一樣是 $O(\log n)$ ，所以對 $n$ 個點做搜尋為 $O(n\log n)$  
當 $n$ 很大時， $n\log n$ 是遠小於 $n^2$ 的  

## Build Quadtree 
逐個將球的座標加入 Quadtree 中，若超過四個球，就將多餘的球加入分裂後的某一方向的 subtree  

## Query 
每個球建立一個範圍，只要搜尋與該範圍重疊的 Quadtree 和其 subtree 中的球，並看有無相交即可  

## Big O of Binary Search Tree
Algorithm(implemented in Python):
```python
def search_recursively(key, node):
    if node is None or node.key == key:
        return node
    if key < node.key:
        return search_recursively(key, node.left)
    # key > node.key
    return search_recursively(key, node.right)
```
$$T(n) = T(\frac{n}{2}) + C_1$$
$$= T(\frac{n}{4}) + 2 * C_1$$
$$= T(\frac{n}{8}) + 3 * C_1$$
$$= T(\frac{n}{2^k}) + k * C_1$$
assume $n = 2^k$, then $k = \log^n_2$
$$T(n) = T(1) + k * C_1 = C_2 + C_1\log^n_2 = O(\log n)$$

## Screenshot
![screenshot1](https://raw.githubusercontent.com/Ming-Chyuan/Quadtree-Collision-Detection/master/img/screenshot1.png)
![screenshot2](https://raw.githubusercontent.com/Ming-Chyuan/Quadtree-Collision-Detection/master/img/screenshot2.png)
![screenshot3](https://raw.githubusercontent.com/Ming-Chyuan/Quadtree-Collision-Detection/master/img/screenshot3.png)
