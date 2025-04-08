import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { Observable } from 'rxjs';
import { Transaction } from '../../models/transaction';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-transactions',
  imports: [AsyncPipe],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css',
})
export class TransactionsComponent {
  transactions$: Observable<Transaction[]>;
  constructor(private transactionService: TransactionService) {
    this.transactions$ = this.transactionService.getTransactions();
  }
}
