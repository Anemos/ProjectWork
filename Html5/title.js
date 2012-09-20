var element;
show('ddd', 'ddd');
element = document.getElementsByTagName('title').item(0);
show('변경전', element.text);

element.text = '제목변경';
show('변경후', element.text);

show('textContent', element.textContent);
show('document.title', document.title );