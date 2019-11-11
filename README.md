# CAIXA SPRING-REST
API de Caixa ATM para executar operações de saque, depósito, transferência, extrato e abertura/fechamento do caixa.


### **Caixa ATM** ###

Método para cadastro do Caixa ATM.

**Método: addAtm**
- POST url: localhost:8080/api/atm/add
- { "name" : "Caixa ATM 1",	"active" : false }

Método para retornar o Caixa ATM.

**Método: getAtm**
- GET url: localhost:8080/api/atm/{id}

Método para alterar o status (ativo/inativo) do Caixa ATM.

**Método: updateAtm**
- PUT url: localhost:8080/api/atm/{id}
- { "name" : "Caixa ATM 1",	"active" : true }

### **Conta** ###

Método para cadastro de conta do cliente.

**Método: addAccount**
- POST url: localhost:8080/api/account/add
- {	"agency" : "0001", "number" : "000001-1",	"balance" : 2500 }

Método para retornar a conta do cliente pelo ID.

**Método: getAccount**
- GET url: localhost:8080/api/account/{id}

Método para retornar o saldo da conta do cliente pelo ID.

**Método: getAccountBalance**
- GET url: localhost:8080/api/account/{id}/balance

Método para retornar o saldo da conta do cliente pelo código da agência e numero da conta.

**Método: getAccountBalance**
- GET url: localhost:8080/api/account/{agency}/{number}/balance

Método para retornar o extrato da conta do cliente pelo código da agência e numero da conta.

**Método: getBankStatement**
- GET url: localhost:8080/api/account/{agency}/{number}/bankstatement

### **Abertura de Caixa ATM** ###

Este controle tem como propósito iniciar o movimento do Caixa ATM informando a quantidade de cédulas.
Cédulas escolhidas: 100, 50, 20, 10, 5.

Método para abrir o movimento do Caixa ATM. Deve ser informado o ID do Caixa ATM e as cédulas com suas respectivas quantidades.

**Método: openAtm**
- PUT url: localhost:8080/api/atmopening/{id}/open
- { "five" : 100,	"ten" : 100, "twenty" : 50,	"fifty" : 100, "hundred" : 100 }

Método para retornar a abertura do movimento do Caixa ATM.

**Método: getOpeningService**
- GET url: localhost:8080/api/atmopening/{id}

Método para fechar o movimento do Caixa ATM. Deve ser informado o ID do Caixa ATM.

**Método: closeAtm**
- PUT url: localhost:8080/api/atmopening/{id}/close

### **Movimento de caixa** ###

Método responsável por salvar movimento de caixa com operações de DEPOSITO, SAQUE e TRANSFERENCIA. O tipo de movimento é indicado conforme ordem do ENUM [MovementType].

- POST localhost:8080/api/atmmovement

- // DEPOSITO
- {
	"account" : {
		"agency" : "0001",
		"number" : "000001-1"
	},
	"atm" : {
		"id" : 1 
	},
	"movementType" : 0,
	"value" : 500
}

- // SAQUE
- {
	"account" : {
		"agency" : "0001",
		"number" : "000001-1"
	},
	"atm" : {
		"id" : 1 
	},
	"movementType" : 1,
	"value" : 500
}

- // TRANSFERENCIA 
- {
	"account" : {
		"agency" : "0001",
		"number" : "000001-1"
	},
	"atm" : {
		"id" : 1 
	},
	"accountDestiny" : {
		"agency" : "0002",
		"number" : "000002-1"
	},
	"movementType" : 2,
	"value" : 500
}















