element = document.getElementsByTagName('script').item(1);
show('text 현재', element.text || element.textContent);

if(browser != 'IE'){
	element.text = "텍스트 노드값 변경";
}
element = document.getElementsByTagName('script').item(1);
show('text 변경', element.text || element.textContent);