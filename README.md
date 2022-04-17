# TradeOperation

##### Steps to run the code <H6>
  - Downlaod the repo.
  - Import the Java Project
  - Right click on project->properties->java Build Path->libraries->Add libraries -> Select JUnit 5-> Select Junit5.

##### Steps to execute the code <H6>
  - Execute TradeOperationsApplicationTests.java & TradeServiceTest.java with Junit Test.


###### PROBLEM STATEMENT <h6> 
  There is a scenario where thousands of trades are flowing into one store, assume any way of transmission of trades. We need to create a one trade store, which stores the trade     in the following order.
  
| First Header | Second Header | Second Header | Second Header | Second Header | Second Header | Second Header |
|--------------|---------------|---------------|---------------|---------------|---------------|---------------|
| Tl           | 1             | CP-1          | B1            | 20/05/2020    | <today date>  | N             |
| T2           | 2             | CP-2          | B1            | 20/05/2021    | <today date>  | N             |
| T2           | 1             | CP-1          | B1            | 20/05/2021    | 20/05/2021    | N             |
| T2           | 3             | CP-3          | B2            | 20/05/2014    | <today date>  | Y             |

###### There are couples of validation, we need to provide in the above assignment <h6> 
- During transmission if the lower version is being received by the store it will reject the trade and throw an exception. If the version is same it will override the existing record.
- Store should not allow the trade which has less maturity date then today date.
- Store should automatically update the expire flag if in a store the trade crosses the maturity date.

  
##### Built information <h6>
- Spring boot application with maven.
- Java Version Used:-JDK 1.8,JRE 1.8
- Junt Version Used- JUnit 5

##### Application endpoints <h6>
http://localhost:8081/api/trade
####[{ "tradeId" : "T1", "version" : 1, "counterPartyId" : "CP-1","bookId" : "B1","maturityDate" : "2022-04-18","createdDate" :"2017-04-22","expired" : "N"}]

http://localhost:8081/h2-console

 ##### Test information <h6>
1. 1st Trade is added with maturity date greater than today's date
2. If version is high and maturity date is greater than today's date then record will be added
3. If version is lower then reject with expcetion.
4. If version is equals to existing version then update
5.Check if Version is Same and date is lower the trade is not updated.
6.Check if Maturity Date is Same as Todays Date the list will be added.
7.Check if version is high but maturity date is low the trade will be rejected.
8.Check If Maturity Date is Expired it will update the Expired Flag
9. Check with T1	1	CP-1	B1	20/05/2020	<today date>	N
10. Check With T2	2	CP-2	B1	20/05/2021	<today date>	N
11. Check With T2	1	CP-1	B1	20/05/2021	14/03/2015	N
12. Check Expired T3	3	CP-3	B2	20/05/2014	<today date>	Y
  
  
###### Test Cases Output <H6>
- All test Case Passed.

