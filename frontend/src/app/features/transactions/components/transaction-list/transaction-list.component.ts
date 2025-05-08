import { Component } from '@angular/core';
import { TransactionService } from '../../services/transaction.service';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { TransactionListItemComponent } from '../transaction-list-item/transaction-list-item.component';
import { Transaction } from '../../models/transaction';

@Component({
  selector: 'app-transaction-list',
  imports: [AsyncPipe, TransactionListItemComponent],
  templateUrl: './transaction-list.component.html',
  styleUrl: './transaction-list.component.css'
})
export class TransactionListComponent {
  transactions$: Observable<Transaction[]>;

  constructor(private transactionService: TransactionService) {
    this.transactions$ = this.transactionService.getTransactions();
  }
}
