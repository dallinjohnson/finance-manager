import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { Observable } from 'rxjs';
import { AsyncPipe, NgIf } from '@angular/common';
import { TransactionListItemComponent } from '../transaction-list-item/transaction-list-item.component';
import { Transaction } from '../../models/transaction';
import { TransactionFormComponent } from '../transaction-form/transaction-form.component';

@Component({
  selector: 'app-transaction-list',
  imports: [AsyncPipe, TransactionListItemComponent, TransactionFormComponent, NgIf],
  templateUrl: './transaction-list.component.html',
  styleUrl: './transaction-list.component.css'
})
export class TransactionListComponent {
  transactions$: Observable<Transaction[]>;

  selectedTransaction: Transaction | undefined;

  constructor(
    private transactionService: TransactionService,
  ) {
    this.transactions$ = this.transactionService.getTransactions();
  }

  onTransactionSelected(transaction: Transaction) {
    this.selectedTransaction = { ...transaction };
  }

  handleTransactionUpdated() {
    this.transactions$ = this.transactionService.getTransactions();
  }

  onFormClosed() {
    this.selectedTransaction = undefined;
  }
}
