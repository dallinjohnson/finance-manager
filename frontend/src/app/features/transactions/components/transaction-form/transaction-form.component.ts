import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Transaction, TransactionDTO } from '../../models/transaction';
import { NgForOf, NgIf } from '@angular/common';
import { TransactionService } from '../../services/transaction.service';
import { Category } from '../../../categories/models/category';
import { Account } from '../../../accounts/models/account';

@Component({
  selector: 'app-transaction-form',
  imports: [ReactiveFormsModule, NgIf, NgForOf],
  templateUrl: './transaction-form.component.html',
  styleUrl: './transaction-form.component.css'
})
export class TransactionFormComponent implements OnInit {
  @Input({ required: true }) transaction!: Transaction;
  @Output() closed = new EventEmitter<void>();
  @Output() transactionUpdated = new EventEmitter<Transaction>();

  transactionForm!: FormGroup;
  categories: Category[] = [];
  accounts: Account[] = [];

  constructor(private transactionService: TransactionService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.transactionForm = this.formBuilder.group({
      title: [this.transaction.title, Validators.required],
      date: [this.transaction.date, Validators.required],
      amount: [this.transaction.amount, Validators.required],
      isCredit: [this.transaction.credit, Validators.required],
      categoryId: [this.transaction.category?.id],
      accountId: [this.transaction.account?.id],
      description: [this.transaction.description]
    })
  }

  save() {
    const updated: Transaction = {
      ...this.transaction,
      ...this.transactionForm.value
    };
    console.log(updated);
    // this.transactionUpdated.emit(updated);
  }

  closeForm() {
    this.closed.emit();
  }

  onSubmit() {
    console.log(this.transaction);
    this.closed.emit();
  }

  saveTransaction() {
    const t: TransactionDTO = {
      title: this.transaction.title,
      date: this.transaction.date,
      credit: this.transaction.credit,
      amount: this.transaction.amount,
      description: this.transaction.description,
      categoryId: this.transaction.category?.id,
      accountId: this.transaction.account?.id
    }

    if (t) {
      console.log(t);
      this.transactionService.updateTransaction(this.transaction.id, t).subscribe({
        next: updatedTransaction => {
          this.transactionUpdated.emit(updatedTransaction);
          this.closed.emit();
        },
        error: error => {
          console.error('Error updating transaction:', error);
        },
        complete: () => {
          console.log('Transaction update complete');
        }
      });
    }
  }
}
