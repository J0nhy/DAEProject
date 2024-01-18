<template>
    <card>
        <h4 slot="header" class="card-title">Create Logistics Operator</h4>
        <form @submit.prevent="create">
            <div class="row">
                <div class="col-md-4">
                    <base-input v-model="user.username" type="text" label="Username" placeholder="Username"></base-input>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <base-input v-model="user.password" type="password" label="password"
                        placeholder="password"></base-input>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <base-input v-model="user.email" type="email" label="email" placeholder="email"></base-input>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <base-input v-model="user.name" type="text" label="name" placeholder="name"></base-input>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <base-input v-model="user.company" type="text" placeholder="FEDEX" label="company"
                        disabled></base-input>

                </div>
            </div>




            <div class="text-center">
                <button type="submit" class="btn btn-success btn-fill float-right">
                    Create Logistics Operator
                </button>
            </div>
            <div class="clearfix"></div>
        </form>
    </card>
</template>
  
  
<style></style>
  
  
<script>
import Card from 'src/components/Cards/Card.vue'

export default {
    components: {
        Card
    },
    data() {
        return {
            user: {
                username: '',
                name: '',
                password: '',
                email: '',
                company: 'FEDEX',
            }
        }
    },
    computed: {
        isFormValid() {
            // Check if all required fields are not empty
            return Object.values(this.user).every(value => value !== "");
        },
    },

    methods: {
        async create() {
            const apiEndpoint = 'packages/api/logisticsoperators'; // Replace with your actual API endpoint

            try {
                if (this.isFormValid) {
                    this.errorMessage = "";
                    const apiUrl = process.env.VUE_APP_API_URL
          const response = await fetch(`${apiUrl}/logisticsoperators`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(this.user),
                    });

                    if (!response.ok) {
                        throw new Error('Failed to create logistics operator');
                    }

                }
                else {
                    this.errorMessage = "Please fill in all required fields.";
                }

            } catch (error) {
                console.error('Error creating logistics operator:', error.message);
                // Handle error as needed
                this.$nextTick(() => {
          this.$refs.errorMessage.offsetHeight; // Trigger a reflow
        });
            }
        }
    }
}
</script>
  
<style></style>
  