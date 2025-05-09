import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { Observable } from 'rxjs';
import { AsyncPipe, NgIf } from '@angular/common';
import { TransactionListItemComponent } from '../transaction-list-item/transaction-list-item.component';
import { Transaction } from '../../models/transaction';
import { TransactionFormComponent } from '../transaction-form/transaction-form.component';

@Component({
  selector: 'app-transaction-list',
  imports: [TransactionListItemComponent, TransactionFormComponent, NgIf],
  templateUrl: './transaction-list.component.html',
  styleUrl: './transaction-list.component.css'
})
export class TransactionListComponent {
  transactions$: Observable<Transaction[]>;
  transactions: Transaction[] = [];

  selectedTransaction: Transaction | undefined;

  constructor(
    private transactionService: TransactionService,
  ) {
    this.transactions$ = this.transactionService.getTransactions();
    this.transactionService.getTransactions().subscribe(transactions => {
      this.transactions = transactions;
    })
  }

  onTransactionSelected(transaction: Transaction) {
    this.selectedTransaction = { ...transaction };
  }

  handleTransactionUpdated(updatedTransaction: Transaction) {
    if (updatedTransaction && updatedTransaction.id) {
      const index = this.transactions.findIndex(t => t.id === updatedTransaction.id);
      if (index !== -1) {
        this.transactions[index] = updatedTransaction;
      }
    }
  }

  onFormClosed() {
    this.selectedTransaction = undefined;
  }
}
