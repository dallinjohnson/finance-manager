import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Transaction } from '../../models/transaction';
import { CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-transaction-list-item',
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './transaction-list-item.component.html',
  styleUrl: './transaction-list-item.component.css'
})
export class TransactionListItemComponent {
  @Input({required: true}) transaction!: Transaction;
  @Output() clicked = new EventEmitter<any>();

  onClick() {
    this.clicked.emit(this.transaction);
  }
}
