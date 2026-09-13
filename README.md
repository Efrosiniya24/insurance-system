## **Содержание**

1 [Модель данных](#модель-данных)

  
## Модель данных
База данных insurance_service сервиса страхования
<img width="2230" height="1605" alt="image" src="https://github.com/user-attachments/assets/7c8ed89b-8854-4e88-8671-78359938a061" />

**Таблицы:**  
**- user_personal_data** - персональные данные сторон (страхователь, застрахованный, выгодоприобретатель), используемые при создании заявки и формировании договора;  
**- application** - заявки на формирование договора;  
**- application_beneficiary** - выгодонолучатели, указанные в заявке;  
**- application_insurance_event** - страховые случаи заявки;  
**- contract**- выпущенный договор;  
**- contract_beneficiary** - выгоднополучатели, указанные в договоре;  
**- contract_insurance_event** - страховые случаи, указанные в договоре;  
**- contract_registry_sync** - статус отправки договора в реестр.  


База данных insurance_registry сервиса эмулятора государственного регистра
<img width="1806" height="1071" alt="image" src="https://github.com/user-attachments/assets/33cd97a6-ed7c-408a-9665-7707f6daeb2a" />

**Таблицы:**  
**- register_user** - персональные данные сторон (страхователь, застрахованный, выгодоприобретатель), указанных в договоре;  
**- registry** - запись регистрации договора;  
**- contract_beneficiary** - выгоднополучатели, указанные в договоре;    
**- application_insurance_event** - страховые случаи записи. 
