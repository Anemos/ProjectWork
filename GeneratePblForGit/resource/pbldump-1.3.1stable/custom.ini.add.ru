;��� ����������� PBLDUMP � FAR ���������� �������� ���������� 
;����� ����� � ����� "c:\program files\far\plugins\multiarc\formats\custom.ini",
;�������������� ������ ������ [PBL] ���� ��� ��� ����,
;��������� PBLDUMP.EXE � ���� �� ��������� ���������� %PATH% 
;� ������������� FAR

[PBL]
;Powerbuilder library format (*.pbl, *.pbd)
ID=48 44 52 2A
IDOnly=1
Extension=pbl
List="pbldump -v"
ExtractWithoutPath=pbldump -eta %%A @%%L
Errorlevel=1
Start="^--"
End="^--"
Format0="nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnzzzzzzzzz dd tt yyyy hh mm ss"
