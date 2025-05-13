import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Transaction, TransactionDTO } from '../../models/transaction';
import { NgForOf, NgIf } from '@angular/common';
import { TransactionService } from '../../services/transaction.service';
import { Category } from '../../../categories/models/category';
import { Account } from '../../../accounts/models/account';
import { AccountService } from '../../../accounts/services/account.service';
import { CategoryService } from '../../../categories/services/category.service';

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

  constructor(
    private transactionService: TransactionService, 
    private accountService: AccountService, 
    private categoryService: CategoryService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.transactionForm = this.formBuilder.group({
      title: [this.transaction.title, Validators.required],
      date: [this.transaction.date, Validators.required],
      amount: [this.transaction.amount, Validators.required],
      credit: [this.toBool(this.transaction.credit), Validators.required],
      categoryId: [this.transaction.category?.id],
      accountId: [this.transaction.account?.id],
      description: [this.transaction.description]
    });

    this.accountService.getAccounts().subscribe({
      next: accounts => this.accounts = accounts,
      error: err => console.error('Failed to load accounts', err)
    });

    this.categoryService.getCategories().subscribe({
      next: categories => this.categories = categories,
      error: err => console.error('Failed to load categories', err)
    });
  }

  save() {
    const updated: TransactionDTO = {
      ...this.transactionForm.value
    };
    this.transactionService.updateTransaction(this.transaction.id, updated).subscribe({
      next: updatedTransaction => {
        this.transactionUpdated.emit(updatedTransaction);
        this.closeForm();
      },
      error: error => {
        console.error('Error updating transaction:', error);
        this.closeForm();
      },
      complete: () => {
        console.log('Transaction update complete');
      }
    });

    console.log(updated);
  }

  closeForm() {
    this.closed.emit();
  }

  onSubmit() {
    console.log(this.transaction);
    if (this.transactionForm.invalid) {
      console.error('Form is invalid');
      return;
    }

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

  toBool(v: any): boolean { return v === true || v === 'true' || v === 1; }
}
